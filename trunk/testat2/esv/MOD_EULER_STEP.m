function [ tnew, ynew ] = MOD_EULER_STEP( told, yold, h, f )
%MOD_EULER_STEP Summary of this function goes here
%   Detailed explanation goes here
    tnew = told + h;
    h2 = h / 2;
    ynew = yold + h * f(told + h2, yold + h2 * f(told, yold));
end

