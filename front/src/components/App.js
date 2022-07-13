import "./App.css";
import Login from "./Login";
import Profile from "./Profile";

import { Routes, Route } from "react-router-dom";
import Feed from "./Feed";
import Mentorship from "./Mentorship/Mentorship";
import Training from "./Training/Training";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="profile" element={<Profile />} />
        <Route path="feed" element={<Feed />} />
        <Route path="mentorship" element={<Mentorship />} />
        <Route path="training" element={<Training />} />
      </Routes>
    </div>
  );
}

export default App;
