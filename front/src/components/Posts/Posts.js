import { ChatIcon, StarIcon } from "@chakra-ui/icons";
import { Box, Badge, Text } from "@chakra-ui/react";
const Posts = ({ postsArr }) => {
  console.log("postsarr", postsArr);
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
              <Badge borderRadius="full" px="2" backgroundColor={"#F5F5F5"}>
                <span>
                  <ChatIcon></ChatIcon> Комментарии
                </span>
              </Badge>
            </Box>
          </Box>
        </Box>
      ))}
    </>
  );
};
export default Posts;
