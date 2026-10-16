package hkc.car_rent_mvc.controller;

import hkc.car_rent_mvc.dto.*;
import hkc.car_rent_mvc.service.AdminService;
import hkc.car_rent_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AppController {
    private UserService userService;
    private AdminService adminService;

    private String adminApiKey;
    private String adminApiKey2;

    @Autowired
    public AppController(
            UserService userService, AdminService adminService,
            @Value("${app.admin.api-key}") String adminApiKey,
            @Value("${app.admin.api-key2}") String adminApiKey2
    ) {
        this.userService = userService;
        this.adminService = adminService;
        this.adminApiKey = adminApiKey;
        this.adminApiKey2 = adminApiKey2;
    }

    //     ------------------------ main ------------------------

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(name = "apiKey", required = false) String apiKey
    ) {
        //http://localhost:8080/?apiKey=adminKey1
        if (adminApiKey.equals(apiKey) || adminApiKey2.equals(apiKey)) {

            AdminPageDTO adminPageDTO = adminService.loadAdminPage();

            model.addAttribute("adminPageDTO", adminPageDTO);
            model.addAttribute("apiKey", apiKey);
            return "admin/admin.html";

        } else {
            return "index.html";
        }
    }

    //     ------------------------ user ------------------------
    @GetMapping("/cars")
    public String loadFreeCars(
            Model model,
            ResDateDTO selectedDateRange
    ) {
        FreeCarsDTO freeCarsDTO = userService.getAllCars(selectedDateRange);

        model.addAttribute("freeCarsDTO", freeCarsDTO);
        return "index.html";
    }

    @GetMapping("/cars/res/start")
    public String loadReservationPage(
            Model model,
            UserSelectionDTO selectedCar
    ) {
        UserSelectionDTO selectedCarForResPageDTO =
                userService.getSelectedCar(selectedCar);

        byte[] picture =
                selectedCarForResPageDTO.getCarDTO().getPicture();

        System.out.println("Car ID: "
                + selectedCarForResPageDTO.getCarDTO().getId());

        System.out.println("Picture is null: " + (picture == null));

        if (picture != null) {
            System.out.println("Picture size: " + picture.length);

            String pictureBase64 = java.util.Base64.getEncoder()
                    .encodeToString(picture);

            model.addAttribute("pictureBase64", pictureBase64);
        }

        model.addAttribute(
                "selectedCarForResPageDTO",
                selectedCarForResPageDTO
        );

        return "user/reservation.html";
    }

    @PostMapping("/cars/res/finish")
    public String saveReservation(
            Model model,
            UserResDTO reservation
    ) {
        SimpleResponseDTO resultText = userService.saveReservation(reservation);

        model.addAttribute("resultText", resultText);
        return "user/result.html";
    }
    //     -------------------------------------------------------

    //     ------------------------ admin ------------------------

    @GetMapping("/admin/car/edit/start")
    public String loadCarEditPage(
            Model model,
            @RequestParam(name = "apiKey", required = false) String apiKey,
            @RequestParam("carId") Integer carId
    ) {
        if (adminApiKey.equals(apiKey) || adminApiKey2.equals(apiKey)) {

            CarDTO selectedCarDTO = adminService.getCarById(carId);

            model.addAttribute("carDTO", selectedCarDTO);
            model.addAttribute("apiKey", apiKey);
            return "admin/adminEditCar.html";

        } else {
            return "index.html";
        }
    }

    @PostMapping("/admin/car/edit/finish")
    public String saveEditedCar(
            Model model,
            @RequestParam(name = "apiKey", required = false) String apiKey,
            NewCarDTO newCarDTO

    ) throws IOException {
        if (adminApiKey.equals(apiKey) || adminApiKey2.equals(apiKey)) {

            SimpleResponseDTO responseDTO = adminService.saveEditedCar(newCarDTO);
            AdminPageDTO adminPageDTO = adminService.loadAdminPage();

            model.addAttribute("responseDTO", responseDTO);
            model.addAttribute("adminPageDTO", adminPageDTO);
            model.addAttribute("apiKey", apiKey);
            return "admin/admin.html";
        } else {
            return "index.html";
        }
    }

    @PostMapping("/admin/car/new")
    public String saveNewCar(
            Model model,
            NewCarDTO newCarDTO,
            @RequestParam(name = "apiKey", required = false) String apiKey
    ) {
        if (adminApiKey.equals(apiKey) || adminApiKey2.equals(apiKey)) {
            SimpleResponseDTO responseDTO = adminService.saveCar(newCarDTO);
            AdminPageDTO adminPageDTO = adminService.loadAdminPage();

            model.addAttribute("responseDTO", responseDTO);
            model.addAttribute("adminPageDTO", adminPageDTO);
            return "admin/admin.html";
        } else {
            return "index.html";
        }
    }

}

