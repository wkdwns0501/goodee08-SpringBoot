package com .example.demo.controller;

import com.example.demo.dto.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExpressionController {

    private final List<Member> members = List.of(
        Member.builder().name("윤서준").email("SeojunYoon@goodee.co.kr").age(10).build(),
        Member.builder().name("윤광철").email("KwangcheolYoon@goodee.co.kr").age(43).build(),
        Member.builder().name("공미영").email("MiyeongKong@goodee.co.kr").age(28).build(),
        Member.builder().name("김도윤").email("DoyunKim@goodee.co.kr").age(11).build()
    );

    @GetMapping("/object")
    public String getMember(Model model) {
        model.addAttribute("member", members.getFirst());

        return "expression/object";
    }

    @GetMapping("/calendars")
    public String getCalendars(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);

        return "expression/calendars";
    }

    @GetMapping("/numbers")
    public String getNumbers(Model model) {
        model.addAttribute("productPrice", 345620.5226);
        model.addAttribute("productCount", 3502340);

        return "expression/numbers";
    }

    @GetMapping("/condition")
    public String getCondition(Model model) {
        model.addAttribute("showWelcome", true);
        model.addAttribute("showDescription", false);

        return "expression/condition";
    }

    @GetMapping("/loop")
    public String getMemberList(Model model) {
        model.addAttribute("members", members);

        return "expression/loop";
    }

    @GetMapping("/link")
    public String getLink(Model model) {
        model.addAttribute("id", 3);

        return "expression/link";
    }

    @GetMapping("/member")
    public String getMemberByIdParam(@RequestParam(name = "id", required = false) Integer id,
                                     Model model) {
        if (id != null) {
            model.addAttribute("member", members.get(id));
        } else {
            model.addAttribute("member", members.getFirst());
        }

        return "expression/member";
    }

    @GetMapping("/member/{id}")
    public String getMemberByIdPath(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("member", members.get(id));

        return "expression/member";
    }

}
