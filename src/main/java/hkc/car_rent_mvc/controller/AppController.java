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
            @RequestParam(name = "apiKey", required = false) String apiKey) {

        if (adminApiKey.equals(apiKey) || adminApiKey2.equals(apiKey)) {
            AdminPageDTO adminPageDTO = adminService.loadAdminPage();
            model.addAttribute("adminPageDTO", adminPageDTO);
            model.addAttribute("adminApiKey", adminApiKey);
            return "admin.html";
        }
        return "index.html";
    }

    //     ------------------------ user ------------------------
    @GetMapping("/cars")
    public String loadFreeCars(
            Model model,
            ResDateDTO selectedDateRange
    ) {
        FreeCarsDTO freeCarsDTO = userService.getFreeCars(selectedDateRange);
        model.addAttribute("freeCarsDTO", freeCarsDTO);
        return "index.html";
    }

    @GetMapping("/cars/res/start")
    public String loadReservationPage(
            Model model,
            UserSelectionDTO selectedCar
    ) {
        UserSelectionDTO selectedCarForResPageDTO = userService.getSelectedCar(selectedCar);
        model.addAttribute("selectedCarForResPageDTO", selectedCarForResPageDTO);
        return "reservation.html";
    }
    @PostMapping("/cars/res/finish")
    public String saveReservation(
            Model model,
            UserResDTO reservation
    ){
        SimpleResponseDTO resultText = userService.saveReservation(reservation);
        model.addAttribute("resultText", resultText);
        return "result.html";
    }

    //     ------------------------ admin ------------------------



}

