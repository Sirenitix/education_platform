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
import { useDisclosure } from "@chakra-ui/react";
import { useEffect, useState, useCallback } from "react";
const Posts = ({ postsArr }) => {
  // console.log("postsarr", postsArr);

  const { isOpen, onOpen, onClose } = useDisclosure();
  const [postData, setPostData] = useState({});

  const getPostData = useCallback(async (postID) => {
    const token = sessionStorage.getItem("access_token");
    console.log(token);
    const arr = await fetch(
      `http://164.92.192.48:8081/reflection/post/${postID}`,
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
    console.log("arr", arr);
    setPostData(arr);
  }, []);

  console.log("post data", postData);
  return (
    <>
      {postsArr.map((p) => (
        <Box
          key={p.id}
          width={"70%"}
          borderWidth="1px"
          borderRadius="lg"
          overflow="hidden"
          marginBottom={"3rem"}
        >
          <Box p="6">
            <Box display="flex" alignItems="baseline">
              <Badge borderRadius="full" px="2" backgroundColor={"#FFCA7A"}>
                Нурсулу Оспан
              </Badge>
              <Box
                color="gray.500"
                fontWeight="semibold"
                letterSpacing="wide"
                fontSize="xs"
                textTransform="uppercase"
                ml="2"
              >
                11 Июля &bull; 12:05
              </Box>
            </Box>
            <Box as="span" color="gray.600" fontSize="sm">
              <Text textAlign={"left"} fontSize="md" margin={"2rem"}>
                {p.title}
              </Text>
              <Text textAlign={"left"} fontSize="md" margin={"2rem"}>
                {p.content}
              </Text>
            </Box>
            <Box display="flex" gap="2rem" alignItems="baseline">
              <Badge
                borderRadius="full"
                px="2"
                backgroundColor={"#F5F5F5"}
                display={"flex"}
                gap="10px"
                fontSize="xs"
              >
                <svg
                  strokeWidth="currentColor"
                  fill="red"
                  viewBox="0 0 512 512"
                  focusable="false"
                  className="chakra-icon css-1i1i0ua"
                  height="1em"
                  width="1em"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path d="M462.3 62.6C407.5 15.9 326 24.3 275.7 76.2L256 96.5l-19.7-20.3C186.1 24.3 104.5 15.9 49.7 62.6c-62.8 53.6-66.1 149.8-9.9 207.9l193.5 199.8c12.5 12.9 32.8 12.9 45.3 0l193.5-199.8c56.3-58.1 53-154.3-9.8-207.9z"></path>
                </svg>{" "}
                <Box>Нравится</Box>
              </Badge>
              <Badge
                // onClick={onOpen}
                borderRadius="full"
                px="2"
                backgroundColor={"#F5F5F5"}
              >
                <span>
                  <ChatIcon></ChatIcon> Комментарии
                </span>
              </Badge>
              <Badge
                onClick={() => {
                  getPostData(p.id);
                  console.log(p.id);
                  onOpen();
                }}
                borderRadius="full"
                px="2"
                backgroundColor={"#F5F5F5"}
              >
                <span>More</span>
              </Badge>
              <Modal isOpen={isOpen} onClose={onClose} size={"xl"}>
                <ModalContent>
                  <ModalCloseButton />
                  <ModalBody>
                    <Box
                      key={p.id}
                      width={"100%"}
                      borderWidth="1px"
                      borderRadius="lg"
                      overflow="hidden"
                      marginBottom={"2rem"}
                      marginTop={"2rem"}
                    >
                      <Box p="6">
                        <Box display="flex" alignItems="baseline">
                          <Badge
                            borderRadius="full"
                            px="2"
                            backgroundColor={"#FFCA7A"}
                          >
                            Нурсулу Оспан
                          </Badge>
                          <Box
                            color="gray.500"
                            fontWeight="semibold"
                            letterSpacing="wide"
                            fontSize="xs"
                            textTransform="uppercase"
                            ml="2"
                          >
                            11 Июля &bull; 12:05
                          </Box>
                        </Box>
                        <Box as="span" color="gray.600" fontSize="sm">
                          <Text
                            textAlign={"left"}
                            fontSize="md"
                            margin={"2rem"}
                          >
                            {p.title}
                          </Text>
                          <Text
                            textAlign={"left"}
                            fontSize="md"
                            margin={"2rem"}
                          >
                            {p.content}
                          </Text>
                          <Text color={"#000"}>
                            {postData?.comment[0]?.content}
                          </Text>
                        </Box>
                      </Box>
                    </Box>
                  </ModalBody>
                  <ModalFooter>
                    <Button variant="ghost" onClick={onClose}>
                      Закрыть
                    </Button>
                  </ModalFooter>
                </ModalContent>
              </Modal>
            </Box>
          </Box>
        </Box>
      ))}
    </>
  );
};
export default Posts;
