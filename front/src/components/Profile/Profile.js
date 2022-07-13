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
import { useDisclosure } from "@chakra-ui/react";
import Posts from "../Posts";
import { useFormik } from "formik";

const Profile = () => {
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

  const { isOpen, onOpen, onClose } = useDisclosure();

  const [data, setData] = useState([]);

  const getUserPosts = useCallback(async () => {
    const token = sessionStorage.getItem("access_token");
    console.log(token);
    const arr = await fetch(
      "http://164.92.192.48:8081/reflection/posts?page=0",
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: ` ${token}`,
        },
      }
    )
      .then((arr) => arr.json())
      .catch((err) => {
        console.error(err);
      });
    setData(arr);
  }, []);

  useEffect(() => {
    getUserPosts();
  }, []);

  console.log("data", data);
  return (
    <div className="profileLayout">
      <div className="navigation">
        <SideBar></SideBar>
      </div>
      <div className="profileBody">
        <Header></Header>
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
                <Tab fontWeight={"600"} marginLeft={"3rem"}>
                  Мои проекты
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
                  <Text fontSize="3xl" margin={"1rem 0"}>
                    {" "}
                    Мои рефлексии:
                  </Text>
                  <Posts postsArr={data} />
                </TabPanel>
                <TabPanel>
                  <AddPost></AddPost>
                </TabPanel>
                <TabPanel>
                  <Button onClick={onOpen}>+ Cоздать проект</Button>

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
                          <input
                            type="hidden"
                            name="remember"
                            defaultValue="true"
                          />
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
