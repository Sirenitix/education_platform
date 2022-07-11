import "./Profile.css";
import Header from "../Header";
import SideBar from "../SideBar";
import UserCard from "../UserCard";
import AddPost from "../AddPost";
import { Service } from "../../service/Service";
import { useState, useEffect, useCallback } from "react";
import {
  Tabs,
  TabList,
  TabPanels,
  Tab,
  TabPanel,
  Box,
  Badge,
  Text,
} from "@chakra-ui/react";
import Posts from "../Posts";

const Profile = () => {
  const [data, setData] = useState([]);

  const getUserPosts = useCallback(async () => {
    const arr = await fetch(`http://164.92.192.48:8081/reflection/posts`, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((arr) => arr.json())
      .catch((err) => {
        console.error(err);
      });
    setData(arr);
  }, []);

  useEffect(() => {
    getUserPosts();
  }, []);

  // console.log("data", data);
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
          <div className="userPosts">
            <Tabs>
              <TabList>
                <Tab fontWeight={"600"}>Мои посты</Tab>
                <Tab fontWeight={"600"} marginLeft={"3rem"}>
                  Написать пост
                </Tab>
              </TabList>

              <TabPanels>
                <TabPanel
                  display={"flex"}
                  flexDirection={"column"}
                  justifyContent={"center"}
                  alignItems={"center"}
                  gap={"2rem"}
                  textAlign={"left"}
                >
                  <Text fontSize="3xl" margin={"2rem 0"}>
                    {" "}
                    Мои рефлексии:
                  </Text>
                  <Posts postsArr={data} />
                </TabPanel>
                <TabPanel>
                  <AddPost></AddPost>
                </TabPanel>
              </TabPanels>
            </Tabs>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Profile;
