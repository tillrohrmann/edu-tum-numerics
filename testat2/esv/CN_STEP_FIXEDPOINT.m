function [ tnew, ynew, iter ] = CN_STEP_FIXEDPOINT( told, yold, h, f )
%CN_STEP_FIXEDPOINT calculates the next time and function value of the
%approximation of the ODE, whose right hand side ist specified by f, by
%using the Crank-Nicolson-Method. In order to solve the implicit form, the
%fixpoint iteration is used.

    TOL = 1e-5;
    maxit = 500;
    ynew = yold;
    tnew = told + h;
    for iter = 1:maxit
       newynew = yold + h / 2 * (f(told, yold) + f(tnew, ynew));  
       if (norm(newynew - ynew) <= max(1, norm(newynew)) * TOL)
            ynew = newynew;
            return
       end
       ynew = newynew;
    end
    
    if(iter > maxit)
        warning('Maximum iteration has been exceeded. The solution is probably not correct.');
    end
end

