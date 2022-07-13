import { ChatIcon } from "@chakra-ui/icons";
import {
  Box,
  Badge,
  Text,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  Button,
} from "@chakra-ui/react";
import Header from "../Header";
import SideBar from "../SideBar";
import { useEffect, useState, useCallback } from "react";
import { Service } from "../../service/Service";
import { useFormik } from "formik";

const Training = ({ postsArr }) => {
  return (
    <>
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
              <Text size={"5xl"}>Coming soon</Text>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default Training;
