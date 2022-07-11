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

const Profile = () => {
  return (
    <>
      <Box
        height={"100%"}
        // border={"1px solid #000"}
        // textAlign={"left"}
        display={"flex"}
        flexDirection={"column"}
        justifyContent={"center"}
        alignItems={"center"}
      >
        <Text margin={"2rem 0"} fontWeight={"600"}>
          О чем Вы хотите поделиться?
        </Text>
        <Textarea
          placeholder="Here is a sample placeholder"
          height={"8rem"}
          marginBottom={"3rem"}
        />{" "}
        <Box>
          <Menu>
            <MenuButton as={Button} rightIcon={<ChevronDownIcon />}>
              Прикрепить
            </MenuButton>
            <MenuList>
              <MenuItem>Download</MenuItem>
              <MenuItem>Create a Copy</MenuItem>
              <MenuItem>Mark as Draft</MenuItem>
              <MenuItem>Delete</MenuItem>
              <MenuItem>Attend a Workshop</MenuItem>
            </MenuList>
          </Menu>
        </Box>
        <Box>
          <Button
            fontWeight={"500"}
            backgroundColor={"#FFCA7A"}
            width={"138px"}
            marginTop={"2rem"}
            display={"block"}
          >
            Добавить
          </Button>
        </Box>
      </Box>
    </>
  );
};
export default Profile;
