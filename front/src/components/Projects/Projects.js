import "./Projects.css";
import { useEffect, useCallback, useState } from "react";
import { Service } from "../../service/Service";
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  Button,
  Box,
  Text,
} from "@chakra-ui/react";
import { useDisclosure } from "@chakra-ui/react";
import { useFormik } from "formik";
const Projects = () => {
  const [projects, setProjects] = useState([]);

  const getProjects = useCallback(async () => {
    const token = sessionStorage.getItem("access_token");
    console.log(token);
    const arr = await fetch(`http://164.92.192.48:8081/projects`, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: ` ${token}`,
      },
    })
      .then((arr) => arr.json())
      .catch((err) => {
        console.error(err);
      });
    console.log(arr);

    setProjects(arr);
  }, []);

  useEffect(() => {
    getProjects();
  }, []);

  const { isOpen, onOpen, onClose } = useDisclosure();
  const service = new Service();
  const validate = (values) => {
    const errors = {};
    return errors;
  };
  const formik = useFormik({
    initialValues: {
      project_name: "",
      users: "",
    },
    validate,
    onSubmit: (values) => {
      alert(JSON.stringify(values, null, 2));
      service.createProject(values);
    },
  });
  return (
    <>
      <div className="project">
        {projects
          ? projects.map((p) => (
              <Box
                key={p.id}
                width="30%"
                borderWidth="1px"
                borderRadius="lg"
                overflow="hidden"
              >
                <Box
                  width={"100%"}
                  height={"120px"}
                  backgroundColor={"#FFCA7A"}
                ></Box>
                <Text margin={"2rem"}> {p.title}</Text>
              </Box>
            ))
          : "[Пока тут пусто, можете добавить новый проект]"}
      </div>
      <div className="addproject">
        <Button margin={"2rem"} onClick={onOpen}>
          + Cоздать проект
        </Button>

        <Modal isOpen={isOpen} onClose={onClose}>
          <ModalOverlay />
          <ModalContent>
            <ModalHeader>Создать новый проект</ModalHeader>
            <ModalCloseButton />
            <ModalBody>
              <form className="mt-8 space-y-6" onSubmit={formik.handleSubmit}>
                <div className="rounded-md shadow-sm -space-y-px">
                  <div color="#000000">
                    <p htmlFor="email-address" className="sr-only">
                      Название проекта
                    </p>
                    <input
                      id="project_name"
                      name="project_name"
                      type="text"
                      required
                      className="loginInput appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                      placeholder="Название проекта"
                      value={formik.values.project_name}
                      onChange={formik.handleChange}
                    />
                  </div>
                  <div>
                    <p htmlFor="password" className="sr-only">
                      Добавить участников проекта:
                    </p>
                    <input
                      id="user"
                      name="users"
                      required
                      className="loginInput appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                      placeholder="Участник 1"
                      value={formik.values.users}
                      onChange={formik.handleChange}
                    />
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
    </>
  );
};
export default Projects;
