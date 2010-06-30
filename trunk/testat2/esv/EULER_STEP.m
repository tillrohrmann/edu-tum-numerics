function [ tnew, ynew ] = EULER_STEP( told, yold, h, f )
    tnew = told + h;
    ynew = yold + h * f(told, yold);
end

