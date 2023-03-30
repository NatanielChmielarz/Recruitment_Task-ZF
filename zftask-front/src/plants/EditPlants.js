import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function EditUser() {
  let navigate = useNavigate();

  const { id } = useParams();

  const[plant,setPlant]=useState({
    name:"",
    species:"",
    description:"",
    user_id:""

})
const{name,species,description,user_id}=plant

  const onInputChange = (e) => {
    setPlant({ ...plant, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadPlant();
  }, []);

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/plant/${id}`, plant);
    navigate("/");
  };

  const loadPlant = async () => {
    const result = await axios.get(`http://localhost:8080/plant/${id}`);
    setPlant(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit Plant</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Name" className="form-label">
                Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter your name"
                name="name"
                value={name}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
            <label htmlFor="Species" className='form-label'>
                      Species
                  </label>
                  <input type={"text"}
                  className="form-control"
                  placeholder='Enter species of the plant'
                  name="species"
                  value={species}
                  onChange={(e)=>onInputChange(e)}
              />
            </div>
            <div className="mb-3">
            <label htmlFor="Description" className='form-label'>
                      Description
                  </label>
                  <input type={"text"}
                  className="form-control"
                  placeholder='Enter the description'
                  name="description"
                  value={description}
                  onChange={(e)=>onInputChange(e)}
              />
            </div>
            <div className="mb-3">
            <label htmlFor="user_id" className='form-label'>
                      user_Id
                  </label>
                  <input type={"text"}
                  className="form-control"
                  placeholder='Enter your ID number'
                  name="user_id"
                  value={user_id}
                  onChange={(e)=>onInputChange(e)}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Submit
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}