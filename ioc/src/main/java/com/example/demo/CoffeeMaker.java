package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CoffeeMaker {
    @Autowired
    @Qualifier("espressoMachine")
    private CoffeeMachine coffeeMachine;

//    public void setCoffeeMachine(CoffeeMachine coffeeMachine) {
//        this.coffeeMachine = coffeeMachine;
//    }

    @PostConstruct
    public void makeCoffee() {
        System.out.println(coffeeMachine.brew());
    }
}
