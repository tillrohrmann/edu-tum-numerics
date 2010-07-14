function [ tnew, ynew ] = EULER_STEP( told, yold, h, f )
% EULER_STEP calculates the next time and function value of the
% approximation of an ODE, whose right hand side is specified by f

    tnew = told + h;
    ynew = yold + h * f(told, yold);
end

