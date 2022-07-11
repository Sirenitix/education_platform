import {
  Box,
  Textarea,
  Text,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
  Button,
} from "@chakra-ui/react";
import { ChevronDownIcon } from "@chakra-ui/icons";
import { Service } from "../../service/Service";
import { useFormik } from "formik";
import "./AddPost.css";

const Profile = () => {
  const service = new Service();
  const validate = (values) => {
    const errors = {};
    const passRegex = /^(.)/g;

    if (!passRegex.test(values.password)) {
      errors.password = "";
    }

    return errors;
  };

  const formik = useFormik({
    initialValues: {
      title: "",
      content: "",
    },
    validate,
    onSubmit: (values) => {
      // event.preventDefault();

      console.log(values);
      alert(JSON.stringify(values, null, 2));
      service.addPost(values);
    },
  });

  return (
    <>
      <Box
        height={"100%"}
        display={"flex"}
        flexDirection={"column"}
        justifyContent={"center"}
        alignItems={"center"}
        alignText={"left"}
      >
        <Text margin={"2rem 0"} fontWeight={"600"}>
          О чем Вы хотите поделиться?
        </Text>
        <form onSubmit={formik.handleSubmit}>
          <Text fontWeight={"600"}>Тема рефлексии</Text>
          <input
            className="postTitle"
            name="title"
            type="text"
            value={formik.values.title}
            onChange={formik.handleChange}
          ></input>
          <Text fontWeight={"600"}>Содержимое</Text>
          <input
            className="postContent"
            name="content"
            value={formik.values.content}
            onChange={formik.handleChange}
            type="text"
          ></input>
          <Box>
            <Menu>
              <MenuButton as={Button} rightIcon={<ChevronDownIcon />}>
                Прикрепить
              </MenuButton>
              <MenuList>
                <MenuItem>Download</MenuItem>
                <MenuItem>Create a Copy</MenuItem>
                <MenuItem>Mark as Draft</MenuItem>
                <MenuItem>Delete </MenuItem>
                <MenuItem>Attend a Workshop</MenuItem>
              </MenuList>
            </Menu>
          </Box>
          <button
            type="submit"
            className="group relative w-full flex justify-center py-4 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            style={{
              backgroundColor: "#F7A325",
              width: "154px",
              height: "50px",
              display: "flex",
              justifyContent: "center",
            }}
          >
            Отправить
          </button>
        </form>
      </Box>
    </>
  );
};
export default Profile;
