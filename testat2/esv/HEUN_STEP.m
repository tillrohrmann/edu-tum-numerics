function [ tnew, ynew ] = HEUN_STEP( told, yold, h, f )
%HEUN_STEP Summary of this function goes here
%   Detailed explanation goes here
    tnew = told + h;
    fold = f(told, yold);
    ynew = yold + h/2 * (fold + f(tnew, yold + h * fold));
end