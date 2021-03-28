package com.example.wallet.service;

import com.example.wallet.entity.Wallet;
import com.example.wallet.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public int defualtAmount;

    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet findByCustId(int custId) {
        return walletRepository.findByCustId(custId);
    }


    public void updateWallet(int custId, int amountUpdate) {
        walletRepository.updateWallet(custId, amountUpdate);
    }

    public void feedInitialData() {

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
                Wallet wallet = new Wallet(customer, initialAmount);
                walletRepository.save(wallet);
            }
            defualtAmount = initialAmount;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("File input.txt not found");
        }
    }

    public void addCustomer(int custId) {
        walletRepository.save(new Wallet(custId, defualtAmount));
    }

    public void updateAllWallet() {
        walletRepository.updateAllWallet(defualtAmount);
    }
}
