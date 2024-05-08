package com.shopAminute.shopAminute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.shopAminute.shopAminute.dataManagers.ClassificationStorage;
import com.shopAminute.shopAminute.handlers.AdminController;
import com.shopAminute.shopAminute.objects.Category;
import com.shopAminute.shopAminute.objects.Product;
import com.shopAminute.shopAminute.сoordinators.ProdService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

public class AdminControllerTest {


    @Test
    public void testAdmin() {
        ProdService prodServiceMock = mock(ProdService.class);
        ClassificationStorage classificationStorageMock = mock(ClassificationStorage.class);
        AdminController adminController = new AdminController(prodServiceMock, classificationStorageMock);
        Model model = mock(Model.class);

        String result = adminController.admin(model);


        assertEquals("admin", result);
        verify(model, times(1)).addAttribute(eq("products"), any());
    }

    @Test
    public void testDeleteProduct() {

        ProdService prodServiceMock = mock(ProdService.class);
        ClassificationStorage classificationStorageMock = mock(ClassificationStorage.class);
        AdminController adminController = new AdminController(prodServiceMock, classificationStorageMock);

        String result = adminController.deleteProduct(1);

        assertEquals("redirect:/admin", result);
        verify(prodServiceMock, times(1)).deleteProduct(1);
    }


    @Test
    public void testAddProductValidationError() throws Exception {

        ProdService prodServiceMock = mock(ProdService.class);
        ClassificationStorage classificationStorageMock = mock(ClassificationStorage.class);
        AdminController adminController = new AdminController(prodServiceMock, classificationStorageMock);
        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);
        MultipartFile mockFile = mock(MultipartFile.class);


        when(classificationStorageMock.findById(anyInt())).thenReturn(Optional.of(new Category()));
        when(bindingResult.hasErrors()).thenReturn(true);


        String result = adminController.addProduct(new Product(), bindingResult, mockFile, mockFile, mockFile, mockFile, mockFile, 1, model);


        assertEquals("product/addProduct", result);
        verify(prodServiceMock, never()).saveProduct(any(), any());
    }



}