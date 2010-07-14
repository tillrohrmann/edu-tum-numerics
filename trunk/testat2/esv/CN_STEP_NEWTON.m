function [ tnew, ynew,iter ] = CN_STEP_NEWTON( told, yold, h, f ,df )
% CN_STEP_NEWTON calculates the next time and function value of the
% approximation of the ODE, whose right hand side is specified by f, by
% using the Crank-Nicolson-Method. In order to solve the implicit form, the
% Newton-Method is applied

maxit = 500;
TOL = 1e-5;

tnew = told + h;

y_current = yold;
fold = f(told,yold);
Id = eye(numel(yold));

%Newton-Iteration
for iter = 1:maxit
    % solve J(x-x_current)=-g(x) with J is Jacobian matrix of g(x) and add
    % solution to x_current in order to obtain x_next
    % g(x) = x-yold -(h/2)*(fold+f(tnew,x))
    y_next = y_current - (Id - (h/2)*df(f,told,y_current))\(y_current-yold-(h/2)*(fold+f(tnew,y_current)));
    
    if(norm(y_next-y_current)<=max(1,norm(y_next))*TOL)
        break;
    end
    
    y_current = y_next;
end

if(iter > maxit)
    warning('Maximum iteration hs been exceeded. The solution is probably not correct.');
end

ynew = y_next;


end

