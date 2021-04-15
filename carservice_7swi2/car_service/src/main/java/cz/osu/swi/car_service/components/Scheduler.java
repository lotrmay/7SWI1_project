package cz.osu.swi.car_service.components;


import cz.osu.swi.car_service.models.Order;
import cz.osu.swi.car_service.models.RegistrationTime;
import cz.osu.swi.car_service.services.OrderService;
import cz.osu.swi.car_service.services.RegistrationTimeService;
import cz.osu.swi.car_service.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.InternetAddress;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class Scheduler {
    private final OrderService orderService;
    private final RegistrationTimeService registrationTimeService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public Scheduler(OrderService orderService, RegistrationTimeService registrationTimeService, JavaMailSender javaMailSender) {
        this.orderService = orderService;
        this.registrationTimeService = registrationTimeService;
        this.javaMailSender = javaMailSender;
     }

    @Scheduled(cron = "0 0 7-14 * * *")
    private void sendNotifications() {
        LocalTime lt = LocalTime.of(LocalTime.now().plusHours(1).getHour(),0,0);
        RegistrationTime curTime = this.registrationTimeService.getRegistrationTimeForOrder(ConversionUtils.getTimeFromString(lt.toString()));
        Order order = this.orderService.getOrderForDateTime(curTime, LocalDate.now());

        if(order == null) return;

        createMessage(order);
    }

    private void createMessage(Order order) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("carservice.swing@gmail.com");
        msg.setTo(order.getCustomer().getEmail());
        msg.setSubject("Připomínka ohledně Vašeho termínu");

        StringBuilder sb = new StringBuilder();
        sb.append("Dobrý den,").append("\n")
                .append("rádi bychom Vás informovali, že se blíží termín Vaši objednávky v našem servisu.").append("\n")
                .append("Dostavte se k nám prosím pár minut před Vaší objednávkou, která je naplánovaná dnes na ").append(order.getTime().getTime()).append("\n").append("\n")
                .append("S přáním krásného dne").append("\n")
                .append("Váš carService");

        msg.setText(sb.toString());
        javaMailSender.send(msg);
    }
}
