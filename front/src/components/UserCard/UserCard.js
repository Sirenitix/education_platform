import { Avatar } from "@chakra-ui/react";
import "./UserCard.css";
import { Table, Tbody, Tr, Td, TableContainer } from "@chakra-ui/react";
import { Button } from "@chakra-ui/react";
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
      <Button
        fontWeight={"500"}
        backgroundColor={"#FFCA7A"}
        width={"138px"}
        marginTop={"2rem"}
      >
        Редактировать
      </Button>
    </div>
  );
};
export default UserCard;
