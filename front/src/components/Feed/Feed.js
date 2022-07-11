// import "./Profile.css";
import Header from "../Header";
import SideBar from "../SideBar";
import UserCard from "../UserCard";
import AddPost from "../AddPost";
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
import { useState, useEffect, useCallback } from "react";

const Feed = () => {
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

  return (
    <div className="profileLayout">
      <div className="navigation">
        <SideBar></SideBar>
      </div>
      <div className="profileBody">
        <div className="header">
          <Header></Header>
        </div>
        <div style={{ paddingLeft: "8rem" }}>
          <div className="feedContent">
            <Text
              fontSize="3xl"
              fontWeight={"600"}
              align={"left"}
              marginBottom={"3rem"}
            >
              Рефлексии других учителей:
            </Text>
            <Posts postsArr={data} />
          </div>
        </div>
      </div>
    </div>
  );
};
export default Feed;
