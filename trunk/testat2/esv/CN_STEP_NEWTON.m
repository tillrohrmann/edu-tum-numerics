function [ tnew, ynew, iter ] = CN_STEP_NEWTON( told, yold, h, f, df )
%CN_STEP_NEWTON Summary of this function goes here
%   Detailed explanation goes here

    % FIXME: this should probably be multi dimensional newton

    TOL = 10^-12;
    tnew = told + h;
    fun = @(ynew)[yold - ynew + h/2 * (f(told, yold) + f(tnew, ynew))];
    dfun = @(ynew)[h / 2 * (df(told, yold) + df(tnew, ynew))];

    iter = 0;
    done = 0;
    ynew = yold;
    while done == 0
       iter = iter + 1;
       newynew = ynew - fun(ynew) / dfun(ynew);
       if (norm(newynew - ynew) < max(1, norm(newynew)) * TOL)
            ynew = newynew;
            return
       end
       ynew = newynew;
    end

end

