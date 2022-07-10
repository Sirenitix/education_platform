import { Avatar } from "@chakra-ui/react";
import "./UserCard.css";
import {
  Table,
  Thead,
  Tbody,
  Tfoot,
  Tr,
  Th,
  Td,
  TableCaption,
  TableContainer,
} from "@chakra-ui/react";
const UserCard = () => {
  return (
    <div className="uCard">
      <div className="avatarBackground"></div>
      <Avatar
        size="2xl"
        src="../../public/avatar.png"
        marginTop={"-80px"}
      />{" "}
      <div className="userName">Нурсулу Оспан</div>
      <div className="title">Личные данные</div>
      <TableContainer>
        <Table
          size="md"
          variant="simple"
          fontSize={"16px"}
          whiteSpace={"pre-wrap"}
        >
          <Tbody>
            <Tr>
              <Td fontWeight={"600"}>Место работы: </Td>
              <Td>Школа-гимназия им. Абая</Td>
            </Tr>
            <Tr>
              <Td fontWeight={"600"}>Профиль:</Td>
              <Td> Биология</Td>
            </Tr>
            <Tr>
              <Td fontWeight={"600"}>Город:</Td>
              <Td>Алматы</Td>
            </Tr>
            <Tr>
              <Td fontWeight={"600"}>Стаж: </Td>
              <Td>12 лет</Td>
            </Tr>
          </Tbody>
        </Table>
      </TableContainer>
    </div>
  );
};
export default UserCard;
