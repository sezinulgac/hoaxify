import { useEffect, useState, useMemo } from "react";
import { signUp } from "./api";
import { Input } from "./components/input";

export function SignUp() {
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState();
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();

  // useEffect(() => {
  //   setErrors({});
  // }, [username]);
  // useEffect(() => {
  //   setErrors({});
  // }, [email]);

  useEffect(() => {
  setErrors((lastErrors) => {
    return {
      ...lastErrors,
      username: undefined
    };
  });
}, [username]);

  useEffect(() => {
  setErrors((lastErrors) => {
    return {
      ...lastErrors,
     email: undefined
    };
  });
}, [email]);

useEffect(() => {
  setErrors((lastErrors) => {
    return {
      ...lastErrors,
     password: undefined
    };
  });
}, [password]);

  useEffect(() => {
  setGeneralError(undefined);
}, [username, email]);



  const onSubmit = async (event) => {
    event.preventDefault();
    setSuccessMessage();
    setGeneralError();
    setApiProgress(true);
    try {
      const response = await signUp({
        username,
        email,
        password,
      });
      setSuccessMessage(response.data.message);
    } catch (axiosError) {
      console.log(axiosError.response.data);
      if (axiosError.response?.data?.status === 400) {
        if (axiosError.response.data.validationErrors) {
          setErrors(axiosError.response.data.validationErrors);
          setGeneralError(
            "Unexpected error ocured. Please try again."
          );
        } else {
          setGeneralError("Unexpected error ocured. Please try again.");
        }
      } 
    } finally {
      setApiProgress(false);
    }
  };

  let passwordRepeatError = useMemo(() => {
  if (password && password !== passwordRepeat) {
   return 'Password mismatch';
  }
  return '';
},[password, passwordRepeat]);
 
  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>Sign Up</h1>
          </div>
          <div className="card-body">
            <Input id="username" label="Username" error={errors.username}
             onChange={(event) => setUsername(event.target.value)}/>
            <Input id = "email" label = "Email" error={errors.email} 
            onChange={(event)=>setEmail(event.target.value)}  />
            <Input id = "password" label = "Password"  error={errors.password} 
            onChange={(event)=>setPassword(event.target.value)} type="password" /> 

 <Input id = "passwordRepeat" label = "Password Repeat"  error={passwordRepeatError} 
            onChange={(event)=>setPasswordRepeat(event.target.value)} type="passwordRepeat" /> 

                      {successMessage && (
              <div className="alert alert-success">{successMessage}</div>
            )}
            {generalError && (
              <div className="alert alert-danger">{generalError}</div>
            )}
            <div className="text-center">
              <button
                disabled={
                  apiProgress || !password || password !== passwordRepeat
                }
                className="btn btn-primary"
                onClick={onSubmit}
              >
                {apiProgress && (
                  <span
                    className="spinner-border spinner-border-sm"
                    aria-hidden="true"
                  ></span>
                )}
                Sign Up
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
