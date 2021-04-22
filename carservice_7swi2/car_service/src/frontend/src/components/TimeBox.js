import React,{useState,useEffect} from "react";
import axios from "axios";

const TimeBox = (props) => {
  const [registrationTimes,setRegistrationTimes] = useState([]);

  const fetchRegistrationTimes = () => {
    axios.get("http://localhost:8080/OrderCreation/RegistrationTimes").then(res => {
      setRegistrationTimes(res.data)
    });
  };

  useEffect(() => {
      fetchRegistrationTimes();
  },[]);

  function setValue(){
    if (typeof(props.AvailableTime) !== 'undefined' &&  props.AvailableTime != null) {
        let temp;

        if (props.AvailableTime.substring(0,props.AvailableTime.lastIndexOf(":")).startsWith("0",0))
            temp = props.AvailableTime.substring(1,props.AvailableTime.lastIndexOf(":"))
        else
            temp = props.AvailableTime.substring(0,props.AvailableTime.lastIndexOf(":"))

        return temp
    }}

  return (
    <div>
      <label htmlFor="time">Čas</label>                    
      <select 
       name="time" id="carTime" value={setValue()}>
        {registrationTimes.map((registrationTime) => 
          <option key={registrationTime.id} value={registrationTime.time}>{registrationTime.time}</option>)}
      </select>
    </div>
)}

export default TimeBox