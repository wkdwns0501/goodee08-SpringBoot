package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

    @GetMapping("/basic")
    public String getMessageBasic() {
        return "message/message-basic";
    }

    @GetMapping("/customer")
    public String getMessageCustomer(Model model) {
        model.addAttribute("name", "구디아카데미");
        model.addAttribute("phone", "02-818-7950");
        return "message/message-customer";
    }

    @GetMapping("/key")
    public String getMessageKey(Model model) {
        model.addAttribute("contactForm", "customer.contact.short");
        model.addAttribute("name", "구디아카데미");
        model.addAttribute("phone", "02-818-7950");

        return "message/message-key";
    }

}
