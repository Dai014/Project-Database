package com.avatar_v2.application;

import com.avatar_v2.controller.GameController;

import java.util.Scanner;

public class AvatarApp {
    private static final int LOGIN         = 1;
    private static final int REGISTRATION  = 2;
    private static final int EXIT_GAME     = 3;

    public void run() {
        int event = 0;
        Scanner scanner = new Scanner(System.in);
        GameController game = new GameController();

        while (event != EXIT_GAME) {
            System.out.println("__________menu game__________");
            System.out.println("1. login");
            System.out.println("2. registration");
            System.out.println("3. exit");
            System.out.print("your choice : ");
            event = scanner.nextInt();
            switch (event) {
                case LOGIN:
                    game.login();
                    break;
                case REGISTRATION:
                    game.registration();
                    break;
                case EXIT_GAME:
                    break;
                default:
                    System.out.println("notice : not valid, enter again");
            }
        }
    }
}
