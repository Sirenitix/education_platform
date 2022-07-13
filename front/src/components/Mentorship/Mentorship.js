import "./Mentorship.css";
import Header from "../Header";
import SideBar from "../SideBar";
import { useEffect, useCallback } from "react";
import { useFormik } from "formik";
import { useDisclosure } from "@chakra-ui/react";
import { Text } from "@chakra-ui/react";
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
      //service.handleLogin(values);
    },
  });

  const getToken = useCallback(async () => {
    const token = sessionStorage.getItem("access_token");
    const result = await fetch(
      "http://164.92.192.48:8081/generateMeetingToken",
      {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: ` ${token}`,
        },
      }
    );
    const postRes = await result.text();
    console.log("token is", postRes);
    return postRes;
  }, []);

  // console.log("checking", meetingToken);
  const getMeetingRoom = useCallback(async (meetingToken) => {
    const options = {
      method: "POST",
      headers: {
        Authorization: `${meetingToken}`,
        "Content-Type": "application/json",
      },
    };
    const url = `https://api.videosdk.live/v2/rooms`;
    const response = await fetch(url, options);
    const data = await response.json();
    console.log(data);
  });

  async function getMeeting() {
    const meetingToken = await getToken();
    console.log("meeting token", meetingToken);
    getMeetingRoom(meetingToken);
  }

  useEffect(() => {
    getMeeting();
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
                <ModalHeader>Создать новый митинг</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                  <form
                    className="mt-8 space-y-6"
                    onSubmit={formik.handleSubmit}
                  >
                    <input type="hidden" name="remember" defaultValue="true" />
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
