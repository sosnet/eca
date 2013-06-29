clear all;
close all;

set(0,'DefaultFigureWindowStyle','docked');

N = 5;  %gridsize
K = 5;  %#points

N2 = N*N;

b = [zeros(1,N2-K), ones(1,K)];
%allSets = unique(b(perms(1:N*N)),'rows');
allSets = uniqueperms(b);
%allSets = [0,1,1,1,0,1,0,0,0,1,1,0,0,0,1,0,0,1,1,1,0,0,0,0,0];
%allSets = [1,1,1,1,0,1,1,1,1];
%allSets = createGrid(N,K);
%allSets = allSets(:)';
numSets = size(allSets, 1);
lambdas = cell(0);
validSets = 0;

filename = ['output/ps_',num2str(N),'_',num2str(K),'.txt'];

for i=1:numSets
    if mod(i, 1000) == 0
        fprintf('Pointset: %d\n', i);
    end
    ps = reshape(allSets(i,:), N, N);
    fp = getFingerPrint(ps);
    if size(fp,1) == 1
        continue;
    end
    found = 0;
    if size(lambdas,2) == 0
        [y,x] = find(ps);
        coords = reshape([x,y]', K*2, 1)';
        dlmwrite(filename, coords, '-append', 'newline', 'pc');
        lambdas{1} = fp;
    else
        for j=1:size(lambdas,2)
            if lambdas{j} == fp
                found = 1;
                break;
            end
        end
        if found == 0
            [y,x] = find(ps);
            coords = reshape([x,y]', K*2, 1)';
            dlmwrite(filename, coords, '-append', 'newline', 'pc');
            lambdas{j+1} = fp;
        end
    end
    validSets = validSets + 1;
end
numFP = size(lambdas,2);

size(allSets, 1)
validSets
numFP
lambdas{1}
