import axios from "axios";
import React, { useEffect,useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewPlant() {
    const[plant,setPlant]=useState({
        name:"",
        species:"",
        description:"",
        user_id:""
    
    })

  const { id } = useParams();

  useEffect(() => {
    loadPlant();
  }, []);

  const loadPlant = async () => {
    const result = await axios.get(`http://localhost:8080/api/plant/${id}`);
    setPlant(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Plant Details</h2>

          <div className="card">
            <div className="card-header">
              Details of plant id : {plant.id}
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>Name:</b>
                  {plant.name}
                </li>
                <li className="list-group-item">
                  <b>Species:</b>
                  {plant.species}
                </li>
                <li className="list-group-item">
                  <b>Description:</b>
                  {plant.description}
                </li>
                
              </ul>
            </div>
          </div>
          <Link className="btn btn-primary my-2" to={"/"}>
            Back to Home
          </Link>
        </div>
      </div>
    </div>
  );
}