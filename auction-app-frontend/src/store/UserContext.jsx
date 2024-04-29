import { createContext, useContext, useState, useEffect } from 'react';
import { set } from 'react-hook-form';
import { getDecodedToken } from 'src/utils/jwtDecode';

const UserContext = createContext(null);

export const useUser = () => useContext(UserContext);

export const UserProvider = ({ children }) => {
  const token = localStorage.getItem("accessToken");

  const [userName, setUserName] = useState(() => {
    return localStorage.getItem("userName");
  });
  const [userType, setUserType] = useState("");
  const [userId, setUserId] = useState("");

  const getUserTypeFromToken = () => {
    const decodedToken = getDecodedToken(token);
  
    setUserType(decodedToken?.role);
  };

  const getUserIdFromToken = () => {
    const decodedToken = getDecodedToken(token);

    setUserId(decodedToken?.id);
  }

  useEffect(() => {
    if (token) {
      getUserTypeFromToken();
      getUserIdFromToken();
    }
  }, [token]);

  useEffect(() => {
    if (userName) {
      localStorage.setItem('userName', userName);
    } else {
      localStorage.removeItem('userName');
    }
  }, [userName]);

  return (
    <UserContext.Provider value={{ userName, setUserName, userType, userId }}>
      {children}
    </UserContext.Provider>
  );
};
