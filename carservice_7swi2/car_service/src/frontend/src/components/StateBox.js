import React,{useState,useEffect} from "react";
import axios from "axios";

const StateBox = () => {
  const [states,setStates] = useState([]);
  const [telephone, setTelephone] = useState("+420");

  const fetchStates = () => {
    axios.get("http://localhost:8080/OrderCreation/States").then(res => {
      setStates(res.data)
      setTelephone(res.data[0].telephone_code)
    });
  };

  useEffect(() => {
      fetchStates();
  },[]);


  return (
    <div>
     <label htmlFor="country">Stát</label>
      <select name="country" id="countrySelect">
        {states.map((state) => 
        <option onClick={(Event) => setTelephone(state.telephone_code)} key={state.id} value={state.state_short}>{state.state_short}</option>)}
      </select>
      <label htmlFor="phone">Telefon</label>
      <input readOnly name="phone" type="text" id="telephoneCode" value={telephone}/>
      <input type="text" id="telephoneInput"/>
    </div>
)}

export default StateBox