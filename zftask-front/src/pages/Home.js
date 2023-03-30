import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from 'axios'
export default function Home() {

   
    const [users, setUsers] = useState([]);
   
    useEffect(()=>{
        loadUsers();
        
    },[]);

    const loadUsers=async()=>{
        const result=await axios.get("http://localhost:8080/users")
        setUsers(result.data);
      
    }
   

    const deleteUser = async (id) => {
      await axios.delete(`http://localhost:8080/user/${id}`);
      loadUsers();
    };
    const deletePlant = async (id) => {
      await axios.delete(`http://localhost:8080/plant/${id}`);
      loadUsers();
    };
    
  return (
    <div className='container'>
        <div className='py-4'>
        <table className="table table shadow">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Username</th>
          <th>Email</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        {users.map(user => (
          <React.Fragment key={user.id}>
            <tr>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>
                  <Link
                    className="btn btn-primary mx-2"
                    to={`/viewuser/${user.id}`}
                  >
                    View
                  </Link>
                  <Link
                    className="btn btn-outline-primary mx-2"
                    to={`/edituser/${user.id}`}
                  >
                    Edit
                  </Link>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deleteUser(user.id)}
                  >
                    Delete
                  </button>
                </td>
            </tr>
            {user.plants.map(plant => (
              
              <tr key={plant.id}>
                <td></td>
                <td>Name: {plant.name}</td>
                <td>Species: {plant.species}</td>
                <td>Description: {plant.description}</td>
                <td>
                  <Link
                    className="btn btn-primary mx-2"
                    to={`/viewplants/${plant.id}`}
                  >
                    View
                  </Link>
                  <Link
                    className="btn btn-outline-primary mx-2"
                    to={`/editplants/${plant.id}`}
                  >
                    Edit
                  </Link>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deletePlant(plant.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </React.Fragment>
        ))}
      </tbody>
    </table>
    </div>
    </div>
  )
}
