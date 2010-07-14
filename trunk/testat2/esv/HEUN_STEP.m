function [ tnew, ynew ] = HEUN_STEP( told, yold, h, f )
%HEUN_STEP calculates the next time and function value of the approximation
%of an ODE, whose right hand side is specified by f
    tnew = told + h;
    fold = f(told, yold);
    ynew = yold + h/2 * (fold + f(tnew, yold + h * fold));
end