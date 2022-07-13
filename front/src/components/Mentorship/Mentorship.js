import "./Mentorship.css";
import Header from "../Header";
import SideBar from "../SideBar";
import UserCard from "../UserCard";
import AddPost from "../AddPost";
import { Service } from "../../service/Service";
import { useState, useEffect, useCallback } from "react";
import { useFormik } from "formik";
import { useDisclosure } from "@chakra-ui/react";
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
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  Button,
} from "@chakra-ui/react";

const Mentorship = () => {
  const validate = (values) => {
    const errors = {};
    const passRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/g;

    if (!passRegex.test(values.password)) {
      errors.password = "";
    }

    return errors;
  };
  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validate,
    onSubmit: (values) => {
      // alert(JSON.stringify(values, null, 2));
      //service.handleLogin(values);
    },
  });

  const getMeetingRoom = useCallback(async () => {
    const options = {
      method: "POST",
      headers: {
        Authorization:
          "$a6d627e2ab765977a2b6c76852ee47b9a0cee43715add7c5e92fe77f06f77b9d",
        "Content-Type": "application/json",
      },
    };
    const url = `https://api.videosdk.live/v2/rooms`;
    const response = await fetch(url, options);
    const data = await response.json();
    console.log(data);
  });

  useEffect(() => {
    getMeetingRoom();
  }, []);

  const { isOpen, onOpen, onClose } = useDisclosure();
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
              Доступные онлайн собрания / митинги
            </Text>
            <Text color="gray.600">[Пока нет доступных ссылок]</Text>
            <Text
              fontSize="3xl"
              fontWeight={"600"}
              align={"left"}
              marginBottom={"3rem"}
            >
              Менторство
            </Text>
            <Button onClick={onOpen}>+ Cоздать митинг</Button>

            <Modal isOpen={isOpen} onClose={onClose}>
              <ModalOverlay />
              <ModalContent>
                <ModalHeader>Создать новый проект</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                  <form
                    className="mt-8 space-y-6"
                    onSubmit={formik.handleSubmit}
                  >
                    <input type="hiddaen" name="remember" defaultValue="true" />
                    <div className="rounded-md shadow-sm -space-y-px">
                      <div color="#000000">
                        <p htmlFor="email-address" className="sr-only">
                          Название проекта
                        </p>
                        <input
                          id="email-address"
                          name="username"
                          type="email"
                          required
                          className="loginInput appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                          placeholder="Название проекта"
                          value={formik.values.username}
                          onChange={formik.handleChange}
                        />
                      </div>
                      <div>
                        <p htmlFor="password" className="sr-only">
                          Добавить участников проекта:
                        </p>
                        <input
                          id="password"
                          name="password"
                          autoComplete="current-password"
                          required
                          className="loginInput appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                          placeholder="Участник 1"
                          value={formik.values.password}
                          onChange={formik.handleChange}
                        />
                        <input
                          id="password"
                          name="password"
                          autoComplete="current-password"
                          required
                          className="loginInput appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                          placeholder="Участник 2"
                          value={formik.values.password}
                          onChange={formik.handleChange}
                        />
                        <input
                          id="password"
                          name="password"
                          autoComplete="current-password"
                          required
                          className="loginInput appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                          placeholder="Участник 3"
                          value={formik.values.password}
                          onChange={formik.handleChange}
                        />
                        {formik.errors.password && (
                          <span>{formik.errors.password}</span>
                        )}
                      </div>
                    </div>
                    <div
                      style={{
                        display: "flex",
                        justifyContent: "center",
                      }}
                    >
                      <button
                        type="submit"
                        className=" loginBtn group relative w-full flex justify-center py-4 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                        style={{
                          backgroundColor: "#BCD7DA",
                          width: "154px",
                          height: "50px",
                          display: "flex",
                          justifyContent: "center",
                        }}
                      >
                        Создать
                      </button>
                    </div>
                  </form>
                </ModalBody>

                <ModalFooter>
                  <Button colorScheme="blue" mr={3} onClick={onClose}>
                    Close
                  </Button>
                </ModalFooter>
              </ModalContent>
            </Modal>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Mentorship;