import React,{useState} from "react";

const ServiceButton = (props) => {
  const [serviceValue, setServiceValue] = useState(0);

  function switchValue(){
    setServiceValue(serviceValue === 1 ? 0 : 1);
  }

  return (
    <button id={props.id} onClick={switchValue} value={serviceValue} type="button" className={serviceValue === 1 ? "service green" : "service white"}>
      <img title={props.id} src={props.imageSrc} alt={props.id} />
      <h2>{props.description}</h2>
    </button>
)}

export default ServiceButton