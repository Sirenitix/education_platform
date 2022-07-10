import "./App.css";
// import Profile from "./Profile";
import Login from "./Login";
import Profile from "./Profile";

import { Routes, Route } from "react-router-dom";
import UserCard from "./UserCard";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="profile" element={<Profile />} />
      </Routes>
      {/* <Login></Login> */}
    </div>
  );
}

export default App;
