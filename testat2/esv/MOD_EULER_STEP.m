function [ tnew, ynew ] = MOD_EULER_STEP( told, yold, h, f )
%MOD_EULER_STEP calculates the next time and function value of the
%approximation of an ODE, whose right hand side is specified by f
    tnew = told + h;
    h2 = h / 2;
    ynew = yold + h * f(told + h2, yold + h2 * f(told, yold));
end

