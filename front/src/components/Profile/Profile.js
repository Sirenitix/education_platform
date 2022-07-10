import "./Profile.css";
import Header from "../Header";
import SideBar from "../SideBar";
import UserCard from "../UserCard";

const Profile = () => {
  return (
    <div className="profileLayout">
      <div className="navigation">
        <SideBar></SideBar>
      </div>
      <div className="profileBody">
        <div className="header">
          <Header></Header>
        </div>
        <div className="profileContent">
          <div className="userInfo">
            <UserCard></UserCard>
          </div>
          <div className="userPosts"></div>
        </div>
      </div>
    </div>
  );
};
export default Profile;
