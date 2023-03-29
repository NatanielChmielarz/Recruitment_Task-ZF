import React, { useEffect, useState } from 'react'

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
                <button className='btn btn-primary mx-1'>View</button>
                <button className='btn btn-outline-primary mx-1'>Edit</button>
                <button className='btn btn-danger mx-1'>Delete</button>             
              </td>
            </tr>
            {user.plants.map(plant => (
              
              <tr key={plant.id}>
                <td></td>
                <td>Name: {plant.name}</td>
                <td>Species: {plant.species}</td>
                <td>Description: {plant.description}</td>
                <td>
                <button className='btn btn-primary mx-1'>View</button>
                <button className='btn btn-outline-primary mx-1'>Edit</button>
                <button className='btn btn-danger mx-1'>Delete</button>             
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
