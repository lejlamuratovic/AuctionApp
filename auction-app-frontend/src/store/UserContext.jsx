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

  const getUserTypeFromToken = () => {
    const decodedToken = getDecodedToken(token);
  
    setUserType(decodedToken?.role);
    console.log("User type", decodedToken?.role)
  };

  useEffect(() => {
    if (token) {
      getUserTypeFromToken();
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
    <UserContext.Provider value={{ userName, setUserName, userType }}>
      {children}
    </UserContext.Provider>
  );
};
