import React,{useState,useEffect} from 'react'
import EmployeeService from '../services/EmployeeService'
import {useNavigate,useParams,Link} from 'react-router-dom';

const AddEmployeeComponent = () => {

   const [firstName, setfirstName] = useState("")
   const [lastName, setlastName] = useState("")
   const [email, setemail] = useState("")
   const navigate = useNavigate();
   const {id} = useParams();
   
   

   const saveOrUpdateEmployee = (e) => {
    e.preventDefault();

    const employee = {firstName, lastName, email}

    if(id){
        EmployeeService.updateEmployee(id, employee)
        .then(
            (response) => {
            navigate('/employees')
        }
        )
        .catch(
            error => {
            console.log(error)
        }
        )

    }else{
        EmployeeService.createEmployee(employee)
        .then(
            (response) =>{

            console.log(response.data)

            navigate('/employees');

        }
        )
        .catch(
            error => {
            console.log(error)
        }
        )
    }
    
}

    useEffect(() => {
        console.log("chp-1");
        //return () => {
            EmployeeService.getEmployeeById(id)
            .then(
                (response)=>{
                    console.log(response.data.payload[0]);
                      setfirstName(response.data.payload[0].firstName)
                      setlastName(response.data.payload[0].lastName)
                      setemail(response.data.payload[0].email)
                }
            )
            .catch(
                (error)=>{
                     console.log(error)
                }
            )
            
        //}
    }, [])


    const title = ()=>{
        if(id){
                return <h2 className = "text-center">Update Employee</h2>
        }else{
               return <h2 className = "text-center">Add Employee</h2>
        }
    }

    return (
        <div>
        <br /><br />
        <div className = "container">
             <div className = "row">
                 <div className = "card col-md-6 offset-md-3 offset-md-3">
                    {
                        title()
                    }
                    
                     <div className = "card-body">
                         <form>
                             <div className = "form-group mb-2">
                                 <label className = "form-label"> First Name :</label>
                                 <input
                                     type = "text"
                                     placeholder = "Enter first name"
                                     name = "firstName"
                                     className = "form-control"
                                     value = {firstName}
                                     onChange = {(e) => setfirstName(e.target.value)}
                                 >
                                 </input>
                             </div>

                             <div className = "form-group mb-2">
                                 <label className = "form-label"> Last Name :</label>
                                 <input
                                     type = "text"
                                     placeholder = "Enter last name"
                                     name = "lastName"
                                     className = "form-control"
                                     value = {lastName}
                                     onChange = {(e) => setlastName(e.target.value)}
                                 >
                                 </input>
                             </div>

                             <div className = "form-group mb-2">
                                 <label className = "form-label"> Email :</label>
                                 <input
                                     type = "email"
                                     placeholder = "Enter email Id"
                                     name = "emailId"
                                     className = "form-control"
                                     value = {email}
                                     onChange = {(e) => setemail(e.target.value)}
                                 >
                                 </input>
                             </div>

                             <button className = "btn btn-success" onClick={(e)=>{saveOrUpdateEmployee(e)}} >Submit </button>
                              <Link to="/employees" className="btn btn-danger"> Cancel </Link>
                         </form>

                     </div>
                 </div>
             </div>

        </div>

     </div>
    )
}

export default AddEmployeeComponent
