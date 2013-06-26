function[minLambda] = getFingerPrint(pointGrid)


fid = fopen('output/lambdas.txt','w');


debug = 1;

N = size(pointGrid,1);
K = sum(pointGrid(pointGrid == 1));

grid_x = meshgrid(1:N, 1:N);
grid_y = meshgrid(1:N, 1:N)';

%pointGrid = createGrid(N, K);

x = grid_x(pointGrid > 0);
y = grid_y(pointGrid > 0);

minLambda = 0;
minGrid = 0;

try
    k = convhull(x,y);
catch 
    return;
end

s = [x(k), y(k)];

for i=1:size(s,1)-1
    l_Grid = labelPoints(pointGrid, N, K, s, i);
    [lambdaMatrix, colinearPointsVector] = getLambdaMatrix(l_Grid);
    
    
    %print to txt file

if fid>=0
    fprintf(fid, '%.0f\n', N);
    fprintf(fid, '%.0f\n', K);
    fprintf(fid, '[');
    for abc = 1:size(lambdaMatrix, 1)
        for def = 1:size(lambdaMatrix, 2)
            fprintf(fid, '%.0f', lambdaMatrix(abc,def));
            if def < size(lambdaMatrix, 1)
                fprintf(fid, ' ');
            end
        end
        if abc < size(lambdaMatrix, 1)
            fprintf(fid, ';');
        end
    end
    fprintf(fid, ']\n\n');
end

    
    %end print to txt file
    
%     if sum(colinearPointsVector) > 0
%         minLambda = 0;
%         return;
%     end
    if minLambda == 0
        minGrid = l_Grid;
        minLambda = lambdaMatrix;
    else
        diff = triu(lambdaMatrix, 1) - triu(minLambda, 1);
        found = 0;
        for row=2:K-1
            for col=row+1:K
                if diff(row,col) > 0
                    found = 1;
                    break;
                end
                if diff(row,col) < 0
                    found = 2;
                    break;
                end
            end
            if found > 0
                break;
            end
        end
        if found == 2
            minLambda = lambdaMatrix;
            minGrid = l_Grid;
        end
    end
end

if debug == 1
    l = minGrid(minGrid > 0);

    labels = cellstr(num2str(l));

    hold all;
    plot(x(k), y(k), '-r', 'LineWidth', 2);
    plot(x, y, 'b+', 'LineWidth', 3);
    text(x, y, labels, 'VerticalAlignment','bottom', ...
                       'HorizontalAlignment','right');
    axis([1 N 1 N]);
    grid on;
    set(gca,'XTick',0:1:N);
    set(gca,'YTick',0:1:N);

    f = dir('output/*.png');
    num = size(f,1)+1;
    print('-dpng', ['output/',num2str(num),'.png']);
    clf;
end


    fclose(fid);
