package com.shopAminute.shopAminute.handlers;

import com.shopAminute.shopAminute.dataManagers.ProductRepo;
import com.shopAminute.shopAminute.сoordinators.ProdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepo productRepository;
    private final ProdService prodService;

    public ProductController(ProductRepo productRepository, ProdService productService) {
        this.productRepository = productRepository;
        this.prodService = productService;
    }
    @GetMapping("")
    public String getAllProduct(Model model)  {
        model.addAttribute("products", prodService.getAllProduct());
        return "/product/product";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model)  {
        model.addAttribute("product", prodService.getProductId(id));
        return "/product/infoProduct";
    }
    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "") String contract, Model model) {
        model.addAttribute("products", prodService.getAllProduct());

        return ((Supplier<String>) () -> {
            if (!ot.isEmpty() & !Do.isEmpty()) {
                if (!price.isEmpty()) {
                    if (price.equals("sorted_by_ascending_price")) {
                        return ((Supplier<String>) () -> {
                            if (!contract.isEmpty()) {
                                if (contract.equals("furniture")) {
                                    model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                                } else if (contract.equals("appliances")) {
                                    model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                                } else if (contract.equals("clothes")) {
                                    model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                                }
                            } else {
                                model.addAttribute("search_product", productRepository.findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                            }
                            return "/product/product";
                        }).get();
                    } else if (price.equals("sorted_by_descending_price")) {
                        return ((Supplier<String>) () -> {
                            if (!contract.isEmpty()) {
                                if (contract.equals("furniture")) {
                                    model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                                } else if (contract.equals("lamp")) {
                                    model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                                } else if (contract.equals("accessories")) {
                                    model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                                }
                            } else {
                                model.addAttribute("search_product", productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                            }
                            return "/product/product";
                        }).get();
                    }
                } else {
                    model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                }
            } else {
                model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
            }
            model.addAttribute("value_search", search);
            model.addAttribute("value_price_ot", ot);
            model.addAttribute("value_price_do", Do);
            return "/product/product";
        }).get();
    }
}