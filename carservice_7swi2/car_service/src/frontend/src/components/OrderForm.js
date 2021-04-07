import React from "react";
import '../css/OrderForm.css';
import ServiceButton from "./ServiceButton";
import StateBox from "./StateBox";
import TimeBox from "./TimeBox";
import imageCarSevis from '../images/autoservis.png';
import imagePneuSevis from '../images/pneuservis.png';
import imageOtherSevices from '../images/otherservices.png';
import CreateOrderButton from "./CreateOrderButton";

const OrderForm = () => {
    return (
        <div id="formWrapper">
        <form id="orderForm">
            <h1 id="mainTitle">Vytvoření objednávky</h1>
            <div id="leftPart">
                <ServiceButton id="carServis" description="Autoservis" imageSrc={imageCarSevis} />
                <ServiceButton id="pneuServis" description="Pneuservis" imageSrc={imagePneuSevis} />
                <ServiceButton id="otherServices" description="Ostatní služby" imageSrc={imageOtherSevices} />
            </div>
            <div id="rightPart">
                <div id="topPart">
                    <div className="line">
                        <label htmlFor="plate">SPZ vozu</label>
                        <input type="text" className="inputBox" id="carPlate" name="plate"/>

                        <label htmlFor="type">Typ vozu</label>
                        <input type="text" className="inputBox" id="carType" name="type"/>

                        <label htmlFor="year">Rok výroby</label>
                        <input type="number" className="inputBox" min="1950" max="2099" step="1" id="carYear" name="year"/>
                    </div>
                    <div id="topRightPart">
                        <div id="topLeftPart">
                            <label htmlFor="orderDate">Datum</label>
                            <input type="date" data-date-format="DD MMMM YYYY" className="inputBox" id="orderDate" name="orderDate"/>

                            <TimeBox/>
                            
                        </div>
                        <div id="topRightRightPart">
                            <CreateOrderButton/>
                        </div>
                    </div>
                    <div id="bottomRightPart">
                        Chybová hláška
                    </div>
                </div>
                <div id="bottomPart">

                <div className="line">
                        <label htmlFor="name">Jméno</label>
                        <input type="text" className="inputBox" id="orderName" name="name"/>

                        <label htmlFor="surname">Příjmení</label>
                        <input type="text" className="inputBox" id="orderSurname" name="surname"/>

                        <label htmlFor="email">Email</label>
                        <input type="text" className="inputBox" id="orderEmail" name="email"/>

                        <label htmlFor="postCode">PSČ</label>
                        <input type="number" min="0" className="inputBox" id="orderPostCode" name="postCode"/>
                    </div>

                    <div className="line">
                        <label htmlFor="city">Město</label>
                        <input type="text" className="inputBox" id="orderCity" name="city"/>

                        <StateBox/>

                        <label htmlFor="street">Ulice a č.p</label>
                        <input id="orderStreet" type="text" name="street"/>
                        <input id="orderStreetCode" type="text"/>
                    </div>

                    <div id="lineArea">
                        <label htmlFor="note">Poznámka</label>
                        <textarea type="text" id="orderNote" name="note"/>
                    </div>
                </div>
            </div>
        </form>
        </div>
      );
}

export default OrderForm