import React,{useState,useEffect} from "react";
import axios from "axios";

const TimeBox = () => {
  const [registrationTimes,setRegistrationTimes] = useState([]);

  const fetchRegistrationTimes = () => {
    axios.get("http://localhost:8080/OrderCreation/RegistrationTimes").then(res => {
      setRegistrationTimes(res.data)
    });
  };

  useEffect(() => {
      fetchRegistrationTimes();
  },[]);


  return (
    <div>
      <label htmlFor="time">Čas</label>                    
      <select name="time" id="carTime">
        {registrationTimes.map((registrationTime) => 
        <option key={registrationTime.id} value={registrationTime.time}>{registrationTime.time}</option>)}
      </select>
    </div>
)}

export default TimeBox