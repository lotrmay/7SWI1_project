import React,{useState} from "react";
import TimeBox from "./TimeBox";

const OrderResult = (props) => {
  const [messageResponse,setMessageResponse] = useState([]);

  function getValues(){
    const carServices = document.getElementById('carServis').value
    const pneuServis = document.getElementById('pneuServis').value
    const otherServices = document.getElementById('otherServices').value

    const carPlate = document.getElementById('carPlate').value
    const carType = document.getElementById('carType').value
    const carYear = document.getElementById('carYear').value
    const orderDate = document.getElementById('orderDate').value
    const carTime = document.getElementById('carTime').value

    const orderName = document.getElementById('orderName').value
    const orderSurname = document.getElementById('orderSurname').value
    const orderEmail = document.getElementById('orderEmail').value
    const orderPostCode = document.getElementById('orderPostCode').value
    const orderCity = document.getElementById('orderCity').value

    const countrySelect = document.getElementById('countrySelect').value
    const telephoneInput = document.getElementById('telephoneInput').value
    const orderStreet = document.getElementById('orderStreet').value
    const orderStreetCode = document.getElementById('orderStreetCode').value
    const note = document.getElementById('orderNote').value

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ 'carPlateTitle': carPlate,'carTypeTitle':carType,'carYearTitle':carYear
        ,'orderDateTitle':orderDate,'orderNameTitle':orderName,'orderSurnameTitle':orderSurname,'orderEmailTitle':orderEmail,
        'orderPostCodeTitle': orderPostCode,'orderCityTitle':orderCity,'countrySelectTitle':countrySelect,'telephoneInputTitle':telephoneInput,
        'orderStreetTitle': orderStreet,'orderStreetCodeTitle':orderStreetCode,'carTimeTitle':carTime,'carServicesTitle':carServices,
        'pneuServisTitle':pneuServis,'otherServicesTitle':otherServices,'note':note})
      };

    fetch('http://localhost:8080/OrderCreation/post', requestOptions)
        .then(response => {
          return response.json().then(response =>{
            setMessageResponse(response);
          })
        })
        .catch(error => console.log(error.message));
  }

  if (typeof( props.AvailableTime) !== 'undefined' &&  props.AvailableTime != null) {
    document.getElementById("orderDate").value=props.AvailableDate;
  }

  return (
    <>
      <div id="topRightPart">
      <div id="topLeftPart">
          <label htmlFor="orderDate">Datum</label>
          <input type="date" className="inputBox" id="orderDate" name="orderDate"/>

          <TimeBox AvailableTime = {props.AvailableTime}/>
          
      </div>
      <div id="topRightRightPart">
      <button id="orderButton" onClick={getValues} type="button">
        <h2>Vytvořit</h2>
      </button>
      </div>
  </div>
  <div id="bottomRightPart" className={messageResponse.status === 202 ? 'greenText' : 'redText' }>
        {messageResponse.message}
  </div>
</>
  
)}

export default OrderResult