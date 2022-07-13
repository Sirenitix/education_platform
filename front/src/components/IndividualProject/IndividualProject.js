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

const IndividualProject = () => {
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
              Проект
            </Text>
            <AddPost></AddPost>
          </div>
        </div>
      </div>
    </div>
  );
};
export default IndividualProject;
