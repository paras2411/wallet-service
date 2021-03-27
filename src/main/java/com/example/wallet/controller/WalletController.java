package com.example.wallet.controller;

import com.example.wallet.entity.Wallet;
import com.example.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/")
    public Wallet saveWallet(@RequestBody Wallet wallet) {

        return walletService.save(wallet);
    }

    @GetMapping("/balance")
    public int getBalance(@RequestParam int custId) {

        Wallet wallet = walletService.findByCustId(custId);
        return wallet.getWalletAmount();
    }

    @GetMapping("/deduct-amount")
    public boolean deductAmount(@RequestParam int custId,
                                @RequestParam int amount) {

        Wallet wallet = walletService.findByCustId(custId);
        int walletAmount = wallet.getWalletAmount();
        if(walletAmount >= amount) {
            walletService.updateWallet(wallet.getCustId(), walletAmount - amount);
            return true;
        }
        return false;
    }

    @GetMapping("/add-amount")
    public boolean addAmount(@RequestParam int custId,
                             @RequestParam int amount) {

        Wallet wallet = walletService.findByCustId(custId);
        walletService.updateWallet(wallet.getCustId(), wallet.getWalletAmount() + amount);
        return true;
    }

    @GetMapping("/reset")
    public void reset() {

        File file = new File("/Users/paraslohani/Documents/IISc/PoDS/input.txt");
        try {
            Scanner scan = new Scanner(file);
            List<Integer> customers = new ArrayList<Integer>();
            int initialAmount = 0;
            int counter = 0;
            while(scan.hasNextLine()) {
                String cur = scan.nextLine();
                if(cur.equals("****")) {
                    counter++;
                }
                else {
                    if(counter == 2) {
                        customers.add(Integer.parseInt(cur));
                    }
                    else if(counter == 3) {
                        initialAmount = Integer.parseInt(cur);
                        break;
                    }
                }
            }
            for(Integer customer: customers) {
                walletService.addWallet(customer, initialAmount);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("File input.txt not found");
        }

    }
}
