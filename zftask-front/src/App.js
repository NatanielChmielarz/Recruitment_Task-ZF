
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/css/bootstrap.css';
import Navbar from './layout/Navbar';
import Home from './pages/Home';
import { BrowserRouter as Router,Routes,Route } from 'react-router-dom';
import AddUser from './users/AddUser';
import AddPlants from './plants/AddPlants';
import EditUser from "./users/EditUser";
import ViewUser from "./users/ViewUser";
import EditPlants from "./plants/EditPlants";
import ViewPlants from "./plants/ViewPlants";
function App() {
  return (
    <div className="App">
      <Router>
      <Navbar/>
        <Routes>
          <Route exact path="/" element={<Home/>}/>
          <Route exact path="/adduser" element={<AddUser/>}/>
          <Route exact path="/addplants" element={<AddPlants/>}/>
          <Route exact path="/edituser/:id" element={<EditUser />} />
          <Route exact path="/editplants/:id" element={<EditPlants />} />
          <Route exact path="/viewuser/:id" element={<ViewUser />} />
          <Route exact path="/viewplants/:id" element={<ViewPlants />} />
        </Routes>
     
      </Router>
    </div>
  );
}

export default App;
