import React,{useState,useEffect} from "react";
import axios from "axios";
import '../css/OrderForm.css';
import ServiceButton from "./ServiceButton";
import StateBox from "./StateBox";
import imageCarService from '../images/autoservis.png';
import imagePneuService from '../images/pneuservis.png';
import imageOtherServices from '../images/otherservices.png';
import OrderResult from "./OrderResult";

const OrderForm = () => {
    const [availableTime,setAvailableTime] = useState([]);

    const fetchSetAvailableTime = () => {
        axios.get("http://localhost:8080/OrderCreation/getAvailableTime").then(res => {
            setAvailableTime(res.data.split('T'))
        });
    };

    useEffect(() => {
        fetchSetAvailableTime();
    },[]);

    return (
        <div id="formWrapper">
        <form id="orderForm" action="javascript:void(0);">
            <h1 id="mainTitle">Vytvoření objednávky</h1>
            <div id="leftPart">
                <ServiceButton id="carServis" description="Autoservis" imageSrc={imageCarService} />
                <ServiceButton id="pneuServis" description="Pneuservis" imageSrc={imagePneuService} />
                <ServiceButton id="otherServices" description="Ostatní služby" imageSrc={imageOtherServices} />
            </div>
            <div id="rightPart">
                <div id="topPart">
                    <div className="line">
                        <label htmlFor="plate">SPZ vozu</label>
                        <input placeholder="Např. 5DB5555" required type="text" className="inputBox" id="carPlate" name="plate"/>

                        <label htmlFor="type">Typ vozu</label>
                        <input placeholder="Např. Sedan" required type="text" className="inputBox" id="carType" name="type"/>

                        <label htmlFor="year">Rok výroby</label>
                        <input placeholder="Např. 2002" required type="number" className="inputBox" min="1950" max="2099" step="1" id="carYear" name="year"/>
                    </div>

                   <OrderResult AvailableDate={availableTime[0]} AvailableTime={availableTime[1]}/>

                </div>
                <div id="bottomPart">

                <div className="line">
                        <label htmlFor="name">Jméno</label>
                        <input placeholder="Např. Tomáš" required type="text" className="inputBox" id="orderName" name="name"/>

                        <label htmlFor="surname">Příjmení</label>
                        <input placeholder="Např. Modrý" required type="text" className="inputBox" id="orderSurname" name="surname"/>

                        <label htmlFor="email">Email</label>
                        <input placeholder="Např. tomas.modry@seznam.cz" required type="text" className="inputBox" id="orderEmail" name="email"/>

                        <label htmlFor="postCode">PSČ</label>
                        <input placeholder="Např. 74159" required type="number" min="0" className="inputBox" id="orderPostCode" name="postCode"/>
                    </div>

                    <div className="line">
                        <label htmlFor="city">Město</label>
                        <input placeholder="Např. Ostrava" required type="text" className="inputBox" id="orderCity" name="city"/>

                        <StateBox/>

                        <label htmlFor="street">Ulice a č.p</label>
                        <input placeholder="Např. Dukla" required id="orderStreet" type="text" name="street"/>
                        <input placeholder="Např. 145" required id="orderStreetCode" type="text"/>
                    </div>

                    <div id="lineArea">
                        <label htmlFor="note">Poznámka</label>
                        <textarea placeholder="Pokud nám chcete něco sdělit..." type="text" id="orderNote" name="note"/>
                    </div>
                </div>
            </div>
        </form>
        </div>
      );
}

export default OrderForm