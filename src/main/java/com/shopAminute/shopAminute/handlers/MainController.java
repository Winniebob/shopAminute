package com.shopAminute.shopAminute.handlers;

import com.shopAminute.shopAminute.settings.Status;
import com.shopAminute.shopAminute.objects.Basket;
import com.shopAminute.shopAminute.objects.Order;
import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.objects.Product;
import com.shopAminute.shopAminute.dataManagers.BasketStorage;
import com.shopAminute.shopAminute.dataManagers.PurchaseStorage;
import com.shopAminute.shopAminute.dataManagers.ProductRepo;
import com.shopAminute.shopAminute.security.PersonDetails;
import com.shopAminute.shopAminute.сoordinators.IndividualHandler;
import com.shopAminute.shopAminute.сoordinators.ProdService;
import com.shopAminute.shopAminute.util.IdentityVerifier;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final ProductRepo productRepo;

    private final IdentityVerifier identityVerifier;
    private final IndividualHandler individualHandler;

    private final ProdService prodService;

    private final BasketStorage basketStorage;

    private final PurchaseStorage purchaseStorage;

    public MainController(ProductRepo productRepo, IdentityVerifier personValidator, IndividualHandler personService, ProdService prodService, BasketStorage basketStorage, PurchaseStorage purchaseStorage) {
        this.productRepo = productRepo;
        this.identityVerifier = personValidator;
        this.individualHandler = personService;
        this.prodService = prodService;
        this.basketStorage = basketStorage;
        this.purchaseStorage = purchaseStorage;
    }

    @GetMapping("/person account")
    public String index(Model model) {
        return ((Supplier<String>) () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            String role = personDetails.getPerson().getRole();
            return role.equals("ROLE_ADMIN") ?
                    "redirect:/admin" :
                    ((Supplier<String>) () -> {
                        model.addAttribute("products", prodService.getAllProduct());
                        return "/user/index";
                    }).get();
        }).get();
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        return ((Supplier<String>) () -> {
            identityVerifier.validate(person, bindingResult);
            return bindingResult.hasErrors() ?
                    "registration" :
                    ((Supplier<String>) () -> {
                        individualHandler.register(person);
                        return "redirect:/person account";
                    }).get();
        }).get();
    }

    @GetMapping("/person account/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", prodService.getProductId(id));
        return "/user/infoProduct";
    }

    @PostMapping("/person account/product/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "") String contract, Model model) {
        return ((Supplier<String>) () -> {
            model.addAttribute("products", prodService.getAllProduct());
            if (!ot.isEmpty() & !Do.isEmpty()) {
                if (!price.isEmpty()) {
                    if (price.equals("sorted_by_ascending_price")) {
                        return ((Supplier<String>) () -> {
                            if (!contract.isEmpty()) {
                                if (contract.equals("furniture")) {
                                    model.addAttribute("search_product", productRepo.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                                } else if (contract.equals("appliances")) {
                                    model.addAttribute("search_product", productRepo.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                                } else if (contract.equals("clothes")) {
                                    model.addAttribute("search_product", productRepo.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                                }
                            } else {
                                model.addAttribute("search_product", productRepo.findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                            }
                            return "/product/product";
                        }).get();
                    } else if (price.equals("sorted_by_descending_price")) {
                        return ((Supplier<String>) () -> {
                            if (!contract.isEmpty()) {
                                if (contract.equals("furniture")) {
                                    model.addAttribute("search_product", productRepo.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                                } else if (contract.equals("appliances")) {
                                    model.addAttribute("search_product", productRepo.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                                } else if (contract.equals("clothes")) {
                                    model.addAttribute("search_product", productRepo.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                                }
                            } else {
                                model.addAttribute("search_product", productRepo.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                            }
                            return "/product/product";
                        }).get();
                    }
                } else {
                    model.addAttribute("search_product", productRepo.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                }
            } else {
                model.addAttribute("search_product", productRepo.findByTitleContainingIgnoreCase(search));
            }
            model.addAttribute("value_search", search);
            model.addAttribute("value_price_ot", ot);
            model.addAttribute("value_price_do", Do);
            return "/product/product";
        }).get();
    }

    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model) {
        Product product = prodService.getProductId(id);
        int id_person = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getId();

        basketStorage.save(new Basket(id_person, product.getId()));

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        int id_person = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getId();
        List<Product> productList = basketStorage.findByPersonId(id_person)
                .stream()
                .map(cart -> prodService.getProductId(cart.getProductId()))
                .collect(Collectors.toList());

        float price = productList.stream().map(Product::getPrice).reduce(0f, Float::sum);

        model.addAttribute("price", price);
        model.addAttribute("cart_product", productList);

        return "/user/cart";
    }


    @GetMapping("/cart/delete/{id}")
    public String deleteProductFromCart(Model model, @PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Basket> cartList = basketStorage.findByPersonId(id_person);
        List<Product> productList = cartList.stream()
                .map(cart -> prodService.getProductId(cart.getProductId()))
                .collect(Collectors.toList());
        basketStorage.deleteCartByProductId(id);
        return "redirect:/cart";
    }

    @GetMapping("/order/create")
    public String order() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Basket> cartList = basketStorage.findByPersonId(id_person);
        List<Product> productList = cartList.stream()
                .map(cart -> prodService.getProductId(cart.getProductId()))
                .collect(Collectors.toList());
        float price = (float) productList.stream()
                .mapToDouble(Product::getPrice)
                .sum();
        String uuid = UUID.randomUUID().toString();
        productList.forEach(product -> {
            Order newOrder = new Order(uuid, product, personDetails.getPerson(), 1, product.getPrice(), Status.Оформлен);
            purchaseStorage.save(newOrder);
            basketStorage.deleteCartByProductId(product.getId());
        });
        return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String orderUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = purchaseStorage.findByPerson(personDetails.getPerson());
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }


}