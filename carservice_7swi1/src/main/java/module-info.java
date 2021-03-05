module carservice_7swi1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires java.persistence;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

    exports cz.osu.carservice;
    exports cz.osu.carservice.controllers;
    exports cz.osu.carservice.models.entities;
    opens cz.osu.carservice.models.entities to org.hibernate.orm.core;
    opens cz.osu.carservice.controllers to javafx.fxml;
    opens cz.osu.carservice to javafx.graphics, javafx.controls, javafx.fxml;

}